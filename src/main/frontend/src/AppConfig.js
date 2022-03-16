import { Toaster } from "react-hot-toast";
import { SWRConfig } from "swr";

export const apiFetch = async (url, init) => {
  const headers = new Headers();
  headers.append("Accept", "application/json");
  const response = await fetch(url, {
    credentials: "same-origin",
    headers,
    ...init,
  });

  if (!response.ok) {
    const error = new Error("An error occurred while fetching the data.");
    error.status = response.status;

    const contentType = response.headers.get("Content-Type");
    if (contentType != null && contentType.indexOf("json") > -1) {
      error.info = await response.json();
    }

    throw error;
  }

  return response.json();
};

export default function AppConfig({ children }) {
  return (
    <SWRConfig
      value={{
        fetcher: apiFetch,
      }}
    >
      <Toaster
        gutter={8}
        toastOptions={{
          duration: 8000,
          success: {
            duration: 3000,
          },
          style: {
            maxWidth: 1000,
          },
        }}
      />
      {children}
    </SWRConfig>
  );
}

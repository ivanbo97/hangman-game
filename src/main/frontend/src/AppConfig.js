import { Toaster } from "react-hot-toast";
import { SWRConfig } from "swr";
import { IntlProvider } from "react-intl";

import useLocale, { LocaleProvider } from "./i18n/LocaleProvider";

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
    <LocaleProvider>
      <LocalizedConfig>
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
      </LocalizedConfig>
    </LocaleProvider>
  );
}

function LocalizedConfig({ children }) {
  const { locale, messages } = useLocale();

  return (
    <IntlProvider {...{ defaultLocale: "en", locale, messages }}>
      <SWRConfig
        value={{
          fetcher: apiFetch,
        }}
      >
        {children}
      </SWRConfig>
    </IntlProvider>
  );
}

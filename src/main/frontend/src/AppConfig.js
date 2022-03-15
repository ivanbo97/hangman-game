import { Toaster } from "react-hot-toast";

export default function AppConfig({ children }) {
  return (
    <>
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
    </>
  );
}

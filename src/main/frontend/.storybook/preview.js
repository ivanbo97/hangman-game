import { IntlProvider } from "react-intl";

const intl = (Story) => (
  <IntlProvider defaultLocale="en">
    <Story />
  </IntlProvider>
);

export const parameters = {
  actions: { argTypesRegex: "^on[A-Z].*" },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

export const decorators = [intl];

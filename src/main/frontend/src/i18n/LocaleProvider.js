import { createContext, useContext, useState } from "react";
import languages from "./lang";

const supportedLanguages = Object.keys(languages);

const LocaleContext = createContext({
  locale: supportedLanguages[0],
  changeLocale: () => {},
});

export default function useLocale() {
  const { locale, changeLocale } = useContext(LocaleContext);

  return {
    locale,
    messages: languages[locale].messages,
    supportedLocales: languages,
    changeLocale,
  };
}

export function LocaleProvider({ children }) {
  const [locale, setLocale] = useState(supportedLanguages[0]);

  const changeLocale = (lang) => {
    if (!supportedLanguages.includes(lang)) {
      console.warn(
        `Try to update locale to ${lang}. Supported locales are ${supportedLanguages}`
      );
      return;
    }

    setLocale(lang);
  };

  return (
    <LocaleContext.Provider value={{ locale, changeLocale }}>
      {children}
    </LocaleContext.Provider>
  );
}

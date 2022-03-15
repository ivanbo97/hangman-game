const fetchClientInternal = async (url, options = {}) => {
  const init = {
    ...options,
  };

  const response = await fetch(url, init);

  if (!response.ok) {
    const body = await response.json();
    throw new Error(body.message);
  }

  const contentLength = response.headers.get("content-length");
  if (contentLength === "0") {
    return;
  }
  const contentType = response.headers.get("content-type");
  if (contentType.includes("json")) {
    return response.json();
  }
};

const fetchClient = {
  get: fetchClientInternal,
  post: (url, options) =>
    fetchClientInternal(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=utf8",
        ...(options?.headers || {}),
      },
      ...options,
    }),
  put: (url, options) =>
    fetchClientInternal(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json;",
        ...(options?.headers || {}),
      },
      ...options,
    }),
};

export default fetchClient;

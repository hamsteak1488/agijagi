const now = (): string => {
  const date = new Date();
  const pad = (num: number) => String(num).padStart(2, '0');

  const isoString =
    date.getFullYear() +
    '-' +
    pad(date.getMonth() + 1) +
    '-' +
    pad(date.getDate()) +
    'T' +
    pad(date.getHours()) +
    ':' +
    pad(date.getMinutes()) +
    ':' +
    pad(date.getSeconds());

  return isoString;
};

export default now;

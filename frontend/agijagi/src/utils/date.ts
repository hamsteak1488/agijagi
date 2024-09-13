export const getReadableTimeDiff = (date: Date): string => {
  const timeDiff = new Date(new Date().getTime() - date.getTime()).getSeconds();

  if (timeDiff < 60) {
    return `${timeDiff}초 전`;
  }

  if (timeDiff < 3600) {
    return `${Math.floor(timeDiff / 60)}분 전`;
  }

  if (timeDiff < 3600 * 24) {
    return `${Math.floor(timeDiff / 3600)}시간 전`;
  }

  return `${Math.floor(timeDiff / 3600 / 24)}일 전`;
};

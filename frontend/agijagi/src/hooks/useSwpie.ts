import { useEffect, useState } from 'react';

const useSwipe = <T extends HTMLElement>(ref: React.RefObject<T>) => {
  const [index, setIndex] = useState<number>(0);

  useEffect(() => {
    if (ref.current === null) {
      return;
    }

    ref.current.style.transform = `translateX(-100%)`;

    const width = ref.current.getBoundingClientRect().width;

    let startX = 0,
      endX = 0,
      down = false;

    const handleTouchStart = (e: TouchEvent) => {
      if (!ref.current) {
        return;
      }

      down = true;
      startX = e.touches[0].clientX;
      endX = 0;
      ref.current.style.transition = '';
    };

    const handleTouchMove = (e: TouchEvent) => {
      if (!down || !ref.current) {
        return;
      }

      endX = Math.min(Math.max(-width, e.touches[0].clientX - startX), width);

      ref.current.style.transform = `translateX(calc(-100% + ${endX}px))`;
    };

    const handleTouchEnd = (e: TouchEvent) => {
      if (!ref.current) {
        return;
      }

      down = false;

      ref.current.style.transition = '';

      if (endX * -1.8 >= width) {
        setIndex((index) => index + 1);
        ref.current.style.transform = `translateX(${endX}px)`;
      }

      if (endX * 1.8 >= width) {
        setIndex((index) => index - 1);
        ref.current.style.transform = `translateX(calc(-200% + ${endX}px))`;
      }

      // eslint-disable-next-line @typescript-eslint/no-unused-expressions
      ref.current.clientWidth;

      ref.current.style.transition = 'transform 0.2s ease';
      ref.current.style.transform = `translateX(-100%)`;
    };

    ref.current.addEventListener('touchstart', handleTouchStart);
    ref.current.addEventListener('touchmove', handleTouchMove);
    ref.current.addEventListener('touchend', handleTouchEnd);

    return () => {
      if (!ref.current) {
        return;
      }

      ref.current.removeEventListener('touchstart', handleTouchStart);
      ref.current.removeEventListener('touchmove', handleTouchMove);
      ref.current.removeEventListener('touchend', handleTouchEnd);
    };
  }, [ref.current]);

  return { index };
};

export default useSwipe;

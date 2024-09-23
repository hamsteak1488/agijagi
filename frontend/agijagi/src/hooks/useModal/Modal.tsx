import React, { useEffect, useRef } from 'react';

import { ModalAnimation, ModalData } from '.';

import styles from './Modal.module.css';

type ModalProps = ModalData;

const classNames: Record<
  ModalAnimation,
  { base: string; enter: string; exit: string }
> = {
  center: {
    base: styles.base,
    enter: styles.enter,
    exit: styles.exit,
  },
  bottom: {
    base: styles['bottom-base'],
    enter: styles['bottom-enter'],
    exit: styles['bottom-exit'],
  },
};

const Modal = ({
  children,
  state,
  animation = 'center',
  onFadeOutEnd,
}: ModalProps) => {
  const wrapperRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!wrapperRef.current) {
      return;
    }

    if (state === 'ACTIVE') {
      wrapperRef.current.className = classNames[animation].base;
      // eslint-disable-next-line @typescript-eslint/no-unused-expressions
      wrapperRef.current.offsetTop;
      wrapperRef.current.classList.add(classNames[animation].enter);
      return;
    }

    wrapperRef.current.className = `${classNames[animation].base} ${classNames[animation].exit}`;
  }, [state, animation, wrapperRef]);

  const handleTransitionEnd = (e: React.TransitionEvent) => {
    if (e.target !== wrapperRef.current) {
      return;
    }

    if (state === 'FADEOUT' && onFadeOutEnd) {
      onFadeOutEnd();
    }
  };

  const handleClose = (e: React.MouseEvent) => {
    if (e.target !== wrapperRef.current || state === 'FADEOUT') {
      return;
    }

    window.history.back();
  };

  return (
    <div
      ref={wrapperRef}
      onTransitionEnd={handleTransitionEnd}
      onClick={handleClose}
    >
      <div onClick={(e) => e.stopPropagation()}>{children}</div>
    </div>
  );
};

export default Modal;

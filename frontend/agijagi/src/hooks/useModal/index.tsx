import {
  createContext,
  ReactElement,
  ReactNode,
  useCallback,
  useContext,
  useEffect,
  useRef,
  useState,
} from 'react';

import Modal from './Modal';

type ModalState = 'ACTIVE' | 'FADEOUT';

export type ModalAnimation = 'center' | 'bottom';

interface ModalPushProps {
  children: ReactElement;
  animation?: ModalAnimation;
  onClose?: () => void;
}

export interface ModalData extends ModalPushProps {
  id: number;
  state: ModalState;
  onFadeOutEnd?: () => void;
}

interface ModalContextType {
  modals: ModalData[];
  push: (data: ModalPushProps) => void;
  pop: () => void;
}

export const ModalContext = createContext<ModalContextType | undefined>(
  undefined
);

export const ModalProvider = ({ children }: { children: ReactNode }) => {
  const [modals, setModals] = useState<ModalData[]>([]);
  const countRef = useRef<number>(0);

  const push = ({ children, animation, onClose }: ModalPushProps) => {
    setModals((modals) => [
      ...modals,
      {
        state: 'ACTIVE',
        id: ++countRef.current,
        children,
        animation,
        onClose,
      },
    ]);
    window.history.pushState({}, '', '');
  };

  const pop = useCallback(() => {
    setModals((modals) => {
      for (let i = modals.length - 1; i >= 0; i--) {
        if (modals[i].state === 'FADEOUT') {
          continue;
        }

        modals[i].state = 'FADEOUT';
        modals[i].onFadeOutEnd = () => {
          setModals((modals) =>
            modals.filter((modal) => {
              if (modal === modals[i] && modal.onClose) {
                modal.onClose();
              }

              return modal !== modals[i];
            })
          );
        };

        return [...modals];
      }

      return modals;
    });
  }, [setModals]);

  useEffect(() => {
    const handlePopState = () => {
      pop();
    };

    window.addEventListener('popstate', handlePopState);

    return () => {
      window.removeEventListener('popstate', handlePopState);
    };
  }, [pop]);

  return (
    <ModalContext.Provider value={{ modals, push, pop }}>
      {children}
      {modals.map((modal) => (
        <Modal key={modal.id} {...modal} />
      ))}
    </ModalContext.Provider>
  );
};

const useModal = () => {
  const modalContext = useContext(ModalContext);

  const pop = () => {
    window.history.back();
  };

  const push = (props: ModalPushProps) => {
    if (!modalContext) {
      return;
    }

    modalContext.push(props);
  };

  return { push, pop };
};

export default useModal;

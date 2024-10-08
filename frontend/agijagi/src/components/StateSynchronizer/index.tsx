import { ReactNode, useEffect, useState } from 'react';

import { useLocation, useNavigate } from 'react-router-dom';
import { getMyInfo } from '../../apis/userApi';
import allowedRoute from '../../constants/allowedRoute';

import useChildStore from '../../stores/useChlidStore';
import useMemberStore from '../../stores/useMemberStore';

interface StateSynchronizerProps {
  children: ReactNode;
}

const StateSynchronizer = ({ children }: StateSynchronizerProps) => {
  const navigate = useNavigate();

  const { pathname } = useLocation();

  const { childId } = useChildStore();

  const { updateMemberId } = useMemberStore();

  const [block, setBlock] = useState<boolean>(true);

  const MAIN = '/main';

  useEffect(() => {
    const update = async () => {
      try {
        const result = await getMyInfo();

        updateMemberId(result.data.memberId);

        if (pathname === MAIN) {
          setBlock(false);
          return;
        }

        if (childId) {
          setBlock(false);
          return;
        }

        setBlock(true);
        navigate(MAIN);
      } catch (error) {
        if (allowedRoute.includes(pathname)) {
          setBlock(false);
          return;
        }

        setBlock(true);
        return;
      }
    };

    update();
  }, [setBlock, pathname]);

  return <>{!block && children}</>;
};

export default StateSynchronizer;

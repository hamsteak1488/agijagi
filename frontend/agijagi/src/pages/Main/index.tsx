import { useQuery } from '@tanstack/react-query';
import { getAllChildren, getUserInfo } from '../../apis/userApi';
import Typhography from '../../components/common/Typography';
import { MyFamily } from '../../components/Main/MyFamily/MyFamily';
import { MyProfile } from '../../components/Main/MyProfile/MyProfile';
import Alert from '../../hooks/useDialog/Alert';
import useMemberStore from '../../stores/useMemberStore';
import { BabyResponse, MemberResponse } from '../../types/user';
import { Container, TitleContainer } from './style';
import { useNavigate } from 'react-router-dom';

export const Main = () => {
  const { memberId } = useMemberStore();
  const navigator = useNavigate();

  const validMemberId =
    memberId !== 0 ? memberId : Number(localStorage.getItem('memberId')) || 0;

  const {
    data: member,
    isLoading,
    error,
  } = useQuery<MemberResponse>({
    queryKey: ['member', validMemberId],
    queryFn: () => {
      if (!validMemberId) {
        return Promise.reject(new Error('유효하지 않은 memberId입니다.'));
      }
      return getUserInfo(validMemberId);
    },
    enabled: !!validMemberId,
  });

  const { data: families = [] } = useQuery<BabyResponse[]>({
    queryKey: ['families'],
    queryFn: getAllChildren,
  });

  if (isLoading)
    return (
      <Container>
        <Typhography size="3xl" color="primary" weight="bold">
          로딩중...
        </Typhography>
      </Container>
    );
  if (error)
    return (
      <Alert onClose={() => {}}>
        <Typhography size="3xl" color="primary" weight="bold">
          오류가 발생했어요
        </Typhography>
      </Alert>
    );

  return (
    <Container>
      <TitleContainer>
        <Typhography size="6xl" weight="bold">
          내 패밀리
        </Typhography>
      </TitleContainer>
      <MyFamily families={families} />

      <TitleContainer>
        <Typhography size="6xl" weight="bold">
          내 프로필
        </Typhography>
      </TitleContainer>
      <MyProfile member={member} />
    </Container>
  );
};

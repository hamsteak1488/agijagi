import styled from '@emotion/styled';
import theme from '../../../styles/theme';
import XIcon from '@heroicons/react/24/solid/XMarkIcon';
import useModal from '../../../hooks/useModal';
import { MemberResponse } from '../../../types/user';
import useChildStore from '../../../stores/useChlidStore';
import { FollowerResponse } from '../../../types/child';
import { useQuery } from '@tanstack/react-query';
import { getAllFollowers } from '../../../apis/childApi';
import { FollowerItem } from './FollowerItem/FollowerItem';

export const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 60vh;
  background-color: ${theme.color.primary[50]};
  border-radius: 2rem 2rem 0rem 0rem;
`;

export const ExitIcon = styled.div`
  position: absolute;
  width: 24px;
  height: 24px;
  left: 87%;
  top: 3%;
`;

export const FollowersContainer = styled.div``;

export interface FollowerModalProps {
  followers: FollowerResponse[];
}
export const FollowerModal = ({ followers }: FollowerModalProps) => {
  const modal = useModal();

  console.log(followers);

  return (
    <Container>
      <ExitIcon onClick={modal.pop}>
        <XIcon />
      </ExitIcon>
      <FollowersContainer>
        {followers.map((item, index) => (
          <FollowerItem key={index} member={item} />
        ))}
      </FollowersContainer>
    </Container>
  );
};

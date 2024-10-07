import styled from '@emotion/styled';
import theme from '../../../styles/theme';
import XIcon from '@heroicons/react/24/solid/XMarkIcon';
import useModal from '../../../hooks/useModal';
import { MemberResponse } from '../../../types/user';

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
export interface FollowerModalProps {
  members?: MemberResponse[];
}
export const FollowerModal = ({ members = [] }: FollowerModalProps) => {
  const modal = useModal();
  return (
    <Container>
      <ExitIcon onClick={modal.pop}>
        <XIcon />
      </ExitIcon>
    </Container>
  );
};

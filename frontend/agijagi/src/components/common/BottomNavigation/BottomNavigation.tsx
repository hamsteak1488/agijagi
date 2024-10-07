import styled from '@emotion/styled';
import HomeIcon from '@heroicons/react/24/outline/HomeIcon';
import BookIcon from '@heroicons/react/24/outline/BookOpenIcon';
import MicIcon from '@heroicons/react/24/outline/MicrophoneIcon';
import RecordIcon from '@heroicons/react/24/outline/DocumentTextIcon';
import defaultBoy from '../../../assets/images/boy.png';
import defaultGirl from '../../../assets/images/boy.png';
import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { getChild } from '../../../apis/childApi';
import useChildStore from '../../../stores/useChlidStore';
import theme from '../../../styles/theme';
import { BabyResponse } from '../../../types/user';
import Typhography from '../Typography';

export const Container = styled.nav`
  flex: 0 0;
  width: 100%;
  height: auto;
`;

export const Bar = styled.div`
  padding-top: 4px;
  width: 100%;
  display: flex;
  justify-content: space-around;
  bottom: 0;
  right: 0;
  background-color: ${theme.color.primary[50]};
  border-top: 2px solid ${theme.color.primary[500]};
  z-index: 2;
  height: 48px;
`;

export const Item = styled(Link)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-decoration: none;

  color: ${theme.color.primary[900]};
  cursor: pointer;

  &.active {
    color: ${theme.color.primary[500]};
  }
`;

export const IconWrapper = styled.div`
  width: 24px;
  height: 24px;
  padding: 2px;
`;

export const ProfileImg = styled.img`
  width: 28px;
  height: 28px;
  border-radius: 50%;
  border: 2px solid ${theme.color.greyScale[700]};
`;

export const BottomNavigation = () => {
  const { childId } = useChildStore();
  const { data: child } = useQuery<BabyResponse>({
    queryKey: ['child', childId],
    queryFn: () => {
      if (childId === 0)
        return Promise.reject(new Error('유효하지 않은 childId입니다.'));
      return getChild(childId);
    },
  });

  return (
    <Container>
      <Bar>
        <Item to={'/family'}>
          <IconWrapper>
            <HomeIcon />
          </IconWrapper>
          <Typhography size="2xs" weight="bold">
            메인
          </Typhography>
        </Item>
        <Item to={'/'}>
          <IconWrapper>
            <BookIcon />
          </IconWrapper>
          <Typhography size="2xs" weight="bold">
            동화
          </Typhography>
        </Item>
        <Item onClick={() => document.body.requestFullscreen()} to={'/'}>
          <IconWrapper>
            <MicIcon />
          </IconWrapper>
          <Typhography size="2xs" weight="bold">
            음성
          </Typhography>
        </Item>
        <Item to={'/record'}>
          <IconWrapper>
            <RecordIcon />
          </IconWrapper>
          <Typhography size="2xs" weight="bold">
            기록
          </Typhography>
        </Item>
        <Item to={'/family/profile'}>
          <ProfileImg
            src={
              child?.imageUrl
                ? child.imageUrl
                : child?.gender === '남아'
                ? defaultBoy
                : defaultGirl
            }
          />
          <Typhography size="2xs" weight="bold">
            {child?.name}
          </Typhography>
        </Item>
      </Bar>
    </Container>
  );
};

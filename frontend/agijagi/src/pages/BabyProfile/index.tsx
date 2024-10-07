import styled from '@emotion/styled';
import CalendarIcon from '@heroicons/react/24/solid/CalendarIcon';
import ReportIcon from '@heroicons/react/24/solid/NewspaperIcon';
import PencilIcon from '@heroicons/react/24/solid/PencilIcon';
import PhotoIcon from '@heroicons/react/24/solid/PhotoIcon';
import InviteCodeIcon from '@heroicons/react/24/solid/UserPlusIcon';
import DeleteIcon from '@heroicons/react/24/solid/XCircleIcon';
import { useQuery } from '@tanstack/react-query';
import { getChild } from '../../apis/childApi';
import { BabyProfileCard } from '../../components/BabyMain/BabyProfileCard/BabyProfileCard';
import { BabyReportCard } from '../../components/BabyMain/BabyReportCard/BabyReportCard';
import { DeleteBaby } from '../../components/BabyMain/DeleteBaby/DeleteBaby';
import { EditBabyImage } from '../../components/BabyMain/EditBabyImage/EditBabyImage';
import { EditBabyInfo } from '../../components/BabyMain/EditBabyInfo/EditBabyInfo';
import Typhography from '../../components/common/Typography';
import useModal from '../../hooks/useModal';
import useChildStore from '../../stores/useChlidStore';
import theme from '../../styles/theme';
import { BabyResponse } from '../../types/user';
import SchedulePage from '../SchedulePage';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: center;
  align-items: center;
`;
export const TitleContainer = styled.div`
  display: flex;
  justify-content: start;
  width: 100%;
  margin: 1rem;
  margin-left: 3rem;
`;

export const MenuConatiner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  gap: 1.25rem;
  width: 85%;
  margin-top: 1rem;
`;

export const IconWrapper = styled.div`
  width: 24px;
  height: 24px;
`;

export const MenuItem = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.66rem;
`;

export const BabyProfile = () => {
  const { childId } = useChildStore();

  const { data: child } = useQuery<BabyResponse>({
    queryKey: ['child', childId],
    queryFn: () => {
      if (childId === 0)
        return Promise.reject(new Error('유효하지 않은 childId입니다.'));
      return getChild(childId);
    },
  });

  const modal = useModal();
  const handleEditBabyInfo = () => {
    modal.push({
      children: <EditBabyInfo child={child} />,
    });
  };
  const handleEditFamilyImage = () => {
    modal.push({
      children: <EditBabyImage />,
    });
  };

  const handelDeleteFamily = () => {
    modal.push({
      children: <DeleteBaby />,
    });
  };

  const handleSchedule = () => {
    modal.push({
      children: <SchedulePage />,
    });
  };

  return (
    <Container>
      <TitleContainer>
        <Typhography size="5xl" weight="bold">
          패밀리 프로필
        </Typhography>
      </TitleContainer>
      <BabyProfileCard child={child}></BabyProfileCard>
      <BabyReportCard></BabyReportCard>
      <MenuConatiner>
        <MenuItem>
          <IconWrapper>
            <ReportIcon />
          </IconWrapper>
          <Typhography weight="bold">성장 분석 보고서 조회</Typhography>
        </MenuItem>
        <MenuItem onClick={handleSchedule}>
          <IconWrapper>
            <CalendarIcon />
          </IconWrapper>
          <Typhography weight="bold">아기 일정 조회</Typhography>
        </MenuItem>
        <MenuItem onClick={handleEditBabyInfo}>
          <IconWrapper>
            <PencilIcon />
          </IconWrapper>
          <Typhography weight="bold">아기 정보 수정하기</Typhography>
        </MenuItem>
        <MenuItem onClick={handleEditFamilyImage}>
          <IconWrapper>
            <PhotoIcon />
          </IconWrapper>
          <Typhography weight="bold">패밀리 이미지 수정하기</Typhography>
        </MenuItem>
        {/* <MenuItem>
          <IconWrapper>
            <FamilyIcon />
          </IconWrapper>
          <Typhography weight="bold">패밀리 관리하기</Typhography>
        </MenuItem> */}
        <MenuItem>
          <IconWrapper>
            <InviteCodeIcon />
          </IconWrapper>
          <Typhography weight="bold">초대코드 생성하기</Typhography>
        </MenuItem>
        <MenuItem onClick={handelDeleteFamily}>
          <IconWrapper>
            <DeleteIcon color={theme.color.danger[500]} />
          </IconWrapper>
          <Typhography color="danger" shade="500" weight="bold">
            패밀리 삭제하기
          </Typhography>
        </MenuItem>
      </MenuConatiner>
    </Container>
  );
};

export default BabyProfile;

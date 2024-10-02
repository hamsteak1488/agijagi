import styled from '@emotion/styled';
import defaultImg from '../../../assets/images/adult.png';
import theme from '../../../styles/theme';
import Typhography from '../../common/Typography';
import UserIcon from '@heroicons/react/16/solid/UserIcon';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 1rem;
`;

export const GridCard = styled.div`
  display: grid;
  grid-template-columns: 1fr 3fr;
  width: 90%;
  height: 100%;
  min-height: 120px;
  max-width: 500px;
  max-height: 180px;
  border-radius: 25px;
  background-color: ${theme.color.primary[50]};
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
`;

export const PhotoSection = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: 1/2;
  padding-left: 1rem;
`;

export const ContentSection = styled.div`
  grid-column: 2/3;
  padding-left: 1rem;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: start;
`;

export const Photo = styled.img`
  width: 100%;
  aspect-ratio: 1;
  border-radius: 50%;
  background-color: ${theme.color.greyScale[200]};
  border: 2px solid ${theme.color.greyScale[500]};
`;

export const MemberInfo = styled.div`
  display: flex;
  position: absolute;
  align-items: center;
  padding: 0.25rem;
  border: 2px solid ${theme.color.primary[900]};
  background-color: ${theme.color.primary[50]};
  border-radius: 0.5rem;
  top: 35%;
  left: 72%;
`;

export const IconWrapper = styled.div`
  width: 24px;
  height: 24px;
`;

export const BabyProfileCard = () => {
  return (
    <Container>
      <GridCard>
        <PhotoSection>
          <Photo src={defaultImg} />
        </PhotoSection>
        <ContentSection>
          <Typhography size="5xl" weight="bold">
            아기다운
          </Typhography>
          <Typhography size="md" color="primary" shade="900" weight="regular">
            + 367일
          </Typhography>
          <MemberInfo>
            <Typhography size="xl" color="primary" weight="extraBold">
              5
            </Typhography>
            <IconWrapper>
              <UserIcon color={theme.color.primary[900]} />
            </IconWrapper>
          </MemberInfo>
        </ContentSection>
      </GridCard>
    </Container>
  );
};

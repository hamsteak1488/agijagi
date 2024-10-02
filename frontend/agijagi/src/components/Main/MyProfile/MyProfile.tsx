import styled from '@emotion/styled';
import defaultImg from '../../../assets/images/adult.png';
import theme from '../../../styles/theme';
import { MemberResponse } from '../../../types/user';
import Typhography from '../../common/Typography';

export const Container = styled.div`
  display: flex;
  justify-content: center;
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
  background-color: ${theme.color.primary[200]};
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
  display: flex;
  justify-content: start;
  align-items: center;
`;

export const Photo = styled.img`
  width: 100%;
  aspect-ratio: 1;
  border-radius: 50%;
  background-color: ${theme.color.greyScale[200]};
  border: 2px solid ${theme.color.greyScale[500]};
`;

export interface MyProfileProps {
  member: MemberResponse | undefined;
}
export const MyProfile = ({ member }: MyProfileProps) => {
  return (
    <Container>
      <GridCard>
        <PhotoSection>
          <Photo src={defaultImg} />
        </PhotoSection>
        <ContentSection>
          <Typhography size="5xl" weight="bold">
            {member?.nickname}
          </Typhography>
        </ContentSection>
      </GridCard>
    </Container>
  );
};

import styled from '@emotion/styled';
import UserIcon from '@heroicons/react/16/solid/UserIcon';
import defaultImg from '../../../assets/images/adult.png';
import theme from '../../../styles/theme';
import Typhography from '../../common/Typography';
import { AddFamily } from '../AddFamily/AddFamily';
import { FamilyItem } from '../FamilyItem/FamilyItem';

export const Container = styled.div`
  display: flex;
  justify-content: center;
`;

export const FamilyGrid = styled.div`
  display: grid;
  justify-items: center;
  grid-template-columns: repeat(2, 1fr);
  width: 100%;
  height: 100%;
  row-gap: 2rem;
`;

export const MyFamily = () => {
  return (
    <Container>
      <FamilyGrid>
        <FamilyItem></FamilyItem>
        <FamilyItem></FamilyItem>
        <AddFamily></AddFamily>
      </FamilyGrid>
    </Container>
  );
};

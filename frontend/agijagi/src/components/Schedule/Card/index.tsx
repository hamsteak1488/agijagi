import Typhography from '../../common/Typography';

import * as s from './style';

interface CardProps {
  title: string;
  description: string;
  time: string;
  onClick?: () => void;
}

const Card = ({ title, description, time, onClick }: CardProps) => {
  return (
    <s.Container onClick={onClick}>
      <Typhography color="white" size="lg">
        {title}
      </Typhography>
      <Typhography color="white" size="sm">
        {time}
      </Typhography>
      <Typhography color="white" size="sm">
        {description}
      </Typhography>
    </s.Container>
  );
};

export default Card;

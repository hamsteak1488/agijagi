import useTimeDifference from '../../../hooks/useTimeDifference';

import { RecordMenu } from '../../../types/record';

import Typhography from '../../common/Typography';

import * as s from './RecordButton.style';

type RecordButtonProps = RecordMenu;

const RecordButton = ({
  icon,
  color,
  type,
  latestDateTime,
}: RecordButtonProps) => {
  const timeDifference = useTimeDifference(new Date(latestDateTime));

  return (
    <s.RecordButton color={color}>
      <s.Icon src={icon} alt="Icon" />
      <Typhography color="white" size="sm">
        {type}
      </Typhography>
      <Typhography color="white" size="md">
        {timeDifference}
      </Typhography>
    </s.RecordButton>
  );
};

export default RecordButton;

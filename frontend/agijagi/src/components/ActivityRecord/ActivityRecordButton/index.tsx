import useTimeDifference from '../../../hooks/useTimeDifference';

import { ActivityRecordMenu } from '../../../types/activityRecord';

import Typhography from '../../common/Typography';

import * as s from './ActivityRecordButton.style';

type ActivityRecordButtonProps = ActivityRecordMenu;

const ActivityRecordButton = ({
  icon,
  color,
  name,
  updatedAt,
}: ActivityRecordButtonProps) => {
  const timeDifference = useTimeDifference(updatedAt);

  return (
    <s.ActivityRecordButton color={color}>
      <s.Icon src={icon} alt="Icon" />
      <Typhography color="white" size="sm">
        {name}
      </Typhography>
      <Typhography color="white" size="md">
        {timeDifference}
      </Typhography>
    </s.ActivityRecordButton>
  );
};

export default ActivityRecordButton;

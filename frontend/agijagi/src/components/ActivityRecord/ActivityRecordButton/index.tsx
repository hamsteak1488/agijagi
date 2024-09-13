import { useEffect, useRef, useState } from 'react';

import { ActivityRecordMenu } from '../../../types/activityRecord';

import { getReadableTimeDiff } from '../../../utils/date';

import Typhography from '../../common/Typography';

import * as s from './ActivityRecordButton.style';

type ActivityRecordButtonProps = ActivityRecordMenu;

const ActivityRecordButton = ({
  icon,
  color,
  name,
  updatedAt,
}: ActivityRecordButtonProps) => {
  const timerRef = useRef<NodeJS.Timer | undefined>(undefined);

  const [timeDiff, setTimeDiff] = useState<string>(
    getReadableTimeDiff(updatedAt)
  );

  useEffect(() => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
    }

    timerRef.current = setInterval(() => {
      setTimeDiff(getReadableTimeDiff(updatedAt));
    }, 1000);
  }, [updatedAt]);

  return (
    <s.ActivityRecordButton color={color}>
      <s.Icon src={icon} alt="Icon" />
      <Typhography color="white" size="sm">
        {name}
      </Typhography>
      <Typhography color="white" size="md">
        {timeDiff}
      </Typhography>
    </s.ActivityRecordButton>
  );
};

export default ActivityRecordButton;

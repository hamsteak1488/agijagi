import useMutateLatestRecords from '../../../hooks/api/useMutateLatestRecords';
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
  const timeDifference = useTimeDifference(new Date(latestDateTime || ''));

  const { mutate, isPending } = useMutateLatestRecords();

  const handleClick = () => {
    mutate({
      childId: 1,
      startDateTime: new Date().toJSON(),
      endDateTime: null,
      type: '대변',
    });
  };

  return (
    <s.RecordButton color={color} onClick={handleClick}>
      <s.Icon src={icon} alt="Icon" />
      <Typhography color="white" size="sm">
        {type}
      </Typhography>
      <Typhography color="white" size="md">
        {latestDateTime === null ? '기록 없음' : timeDifference}
      </Typhography>
    </s.RecordButton>
  );
};

export default RecordButton;

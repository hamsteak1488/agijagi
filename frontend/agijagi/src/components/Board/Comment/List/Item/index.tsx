import useDialog from '../../../../../hooks/useDialog';

import { getReadableTimeDiff } from '../../../../../utils/getReadableTimeDiff';

import Typhography from '../../../../common/Typography';

import * as s from './style';

interface ItemProps {
  writer: string;
  body: string;
  createdAt: Date;
}

const Item = ({ writer, body, createdAt }: ItemProps) => {
  const { confirm } = useDialog();

  const handleClick = async () => {
    if (!(await confirm('댓글을 삭제할까요?'))) {
      return;
    }
  };

  return (
    <s.Container onClick={handleClick}>
      <s.Detail>
        <Typhography color="primary">{writer}</Typhography>
        <Typhography color="secondary" size="sm">
          {getReadableTimeDiff(createdAt)}
        </Typhography>
      </s.Detail>
      {body}
    </s.Container>
  );
};

export default Item;

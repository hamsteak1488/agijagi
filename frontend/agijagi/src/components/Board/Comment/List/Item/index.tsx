import { getReadableTimeDiff } from '../../../../../utils/getReadableTimeDiff';
import Typhography from '../../../../common/Typography';
import * as s from './style';

interface ItemProps {
  writer: string;
  body: string;
  createdAt: Date;
}

const Item = ({ writer, body, createdAt }: ItemProps) => {
  return (
    <s.Container>
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

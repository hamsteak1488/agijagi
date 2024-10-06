import { getReadableTimeDiff } from '../../../../utils/getReadableTimeDiff';

import Typhography from '../../../common/Typography';
import ArticleList from '..';

import * as s from './Item.style';

interface ItemProps {
  id: number;
  image: string;
  title?: string;
  description: string;
  writer: string;
  createdAt: string;
  onClick?: () => void;
}

const Item = ({
  title,
  description,
  writer,
  createdAt,
  onClick,
}: ItemProps) => {
  return (
    <s.Container onClick={onClick}>
      {!!title && (
        <Typhography color="primary" weight="bold" shade="900">
          {title}
        </Typhography>
      )}
      <s.ImageList>
        <ArticleList.Item.Image
          src="https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F14893%2Ftbwebq9cfafci2mr__400_400.jpg&w=400&q=75"
          alt="게시글 이미지"
        />
        <ArticleList.Item.Image
          src="https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F1446%2Fptue2me96vmp93zw__400_400.jpg&w=400&q=75"
          alt="게시글 이미지"
        />
        <ArticleList.Item.Image
          src="https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F50377%2F5qmo4wsjs08ahqc8__400_400.jpg&w=400&q=75"
          alt="게시글 이미지"
        />
      </s.ImageList>
      <Typhography>{description}</Typhography>
      <s.Detail>
        <Typhography size="sm" color="primary">
          {writer}
        </Typhography>
        <Typhography size="sm" color="secondary">
          {getReadableTimeDiff(new Date(createdAt))}
        </Typhography>
      </s.Detail>
    </s.Container>
  );
};

export default Item;

import { getReadableTimeDiff } from '../../../../utils/getReadableTimeDiff';

import Typhography from '../../../common/Typography';
import ArticleList from '..';

import * as s from './Item.style';

interface ItemProps {
  id: number;
  images: string[];
  title?: string;
  description: string;
  writer: string;
  createdAt: string;
  onClick?: () => void;
}

const Item = ({
  id,
  images,
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
      {images.length && (
        <s.ImageList>
          {images.map((image, index) => (
            <ArticleList.Item.Image
              key={index}
              src={image}
              alt={`게시글 ${index + 1}번 이미지`}
            />
          ))}
        </s.ImageList>
      )}
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

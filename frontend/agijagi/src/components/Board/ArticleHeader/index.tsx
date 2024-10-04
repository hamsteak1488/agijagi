import * as s from './style';

import Button from '../../common/Button';

import useDialog from '../../../hooks/useDialog';

interface ArticleHeaderProps {
  title: string;
  articleId: number;
  isAuthor: boolean;
}

const ArticleHeader = ({ title, articleId, isAuthor }: ArticleHeaderProps) => {
  const { confirm } = useDialog();

  const handleDeleteClick = async () => {
    if (!(await confirm('정말로 삭제할까요?'))) {
      return;
    }
    alert('삭제');
  };

  return (
    <s.Container>
      <s.Title>{title}</s.Title>
      {isAuthor && (
        <s.Menu>
          <Button color="danger" size="sm" onClick={handleDeleteClick}>
            삭제
          </Button>
        </s.Menu>
      )}
    </s.Container>
  );
};

export default ArticleHeader;

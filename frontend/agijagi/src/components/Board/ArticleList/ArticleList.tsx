import { ReactNode } from 'react';

import * as s from './ArticleList.style';

interface ArticleListProps {
  children: ReactNode;
}

const ArticleList = ({ children }: ArticleListProps) => {
  return <s.Container>{children}</s.Container>;
};

export default ArticleList;

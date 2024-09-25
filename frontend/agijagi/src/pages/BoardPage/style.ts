import styled from '@emotion/styled';

import theme from '../../styles/theme';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
`;

export const Main = styled.main`
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
  overflow-y: scroll;
  padding: 0;
`;

export const ArticleList = styled.div`
  overflow-y: scroll;
  border-top: 0.0625rem solid ${theme.color.primary[500]};
`;

export const ArticleListItem = styled.div`
  margin-top: -3.5rem;

  :first-of-type {
    margin-top: 0;
  }

  :not(:last-of-type) {
    > div {
      padding-bottom: 2rem;
    }
  }
`;

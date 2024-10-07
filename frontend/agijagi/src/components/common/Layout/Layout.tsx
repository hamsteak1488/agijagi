import { ReactNode } from 'react';

import * as s from './Layout.style';

interface LayoutProps {
  children: ReactNode;
}

const Layout = ({ children }: LayoutProps) => {
  return <s.Layout>{children}</s.Layout>;
};

export default Layout;

import { ReactNode } from 'react';

import * as s from './CustomizedBorderContainer.style';

interface CustomizedBorderContainerProps {
  children: ReactNode;
  height?: string;
  border: string;
  borderHeight: string;
  backgroundColor: string;
}

const CustomizedBorderContainer = ({
  children,
  height = '100%',
  border,
  borderHeight,
  backgroundColor,
}: CustomizedBorderContainerProps) => {
  return (
    <s.Container style={{ height }}>
      <s.Border
        color={backgroundColor}
        image={border}
        height={borderHeight}
      ></s.Border>
      <s.Body color={backgroundColor}>{children}</s.Body>
    </s.Container>
  );
};

export default CustomizedBorderContainer;

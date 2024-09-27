import * as s from './style';

import Button from '../Button';
import Typhography from '../Typography';

import type { FallbackProps } from 'react-error-boundary';

import useInvalidateErrorCache from '../../../hooks/useInvalidateErrorCache';

interface ErrorBoundaryFallbackProps extends FallbackProps {
  children: string;
  height?: string;
}

const ErrorBoundaryFallback = ({
  children,
  height = '100%',
  resetErrorBoundary,
}: ErrorBoundaryFallbackProps) => {
  const { invalidate } = useInvalidateErrorCache();

  const handleResetClick = () => {
    invalidate();
    resetErrorBoundary();
  };

  return (
    <s.Container style={{ height }}>
      <Typhography>{children}</Typhography>
      <Button color="danger" size="sm" onClick={handleResetClick}>
        재시도
      </Button>
    </s.Container>
  );
};

export default ErrorBoundaryFallback;

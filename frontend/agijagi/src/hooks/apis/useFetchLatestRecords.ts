import { useSuspenseQuery } from '@tanstack/react-query';

import { getLatestRecords } from '../../apis/record';

const useFetchLatestRecords = () => {
  const { data, error, isFetching } = useSuspenseQuery({
    queryKey: ['record', 'latest'],
    queryFn: getLatestRecords,
  });

  if (error && !isFetching) {
    throw error;
  }

  return data;
};

export default useFetchLatestRecords;

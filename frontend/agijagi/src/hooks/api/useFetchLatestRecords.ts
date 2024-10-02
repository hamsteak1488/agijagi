import { useSuspenseQuery } from '@tanstack/react-query';

import { getLatestRecords } from '../../apis/record';

const queryKey = ['record', 'latest'];

const useFetchLatestRecords = () => {
  const { data, error, isFetching } = useSuspenseQuery({
    queryKey,
    queryFn: getLatestRecords,
  });

  if (error && !isFetching) {
    throw error;
  }

  return data;
};

useFetchLatestRecords.queryKey = queryKey;

export default useFetchLatestRecords;

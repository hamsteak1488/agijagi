import { useMutation, useQueryClient } from '@tanstack/react-query';

import { addRecord } from '../../apis/record';
import useDialog from '../useDialog';
import useFetchLatestRecords from './useFetchLatestRecords';

const useAddLatestRecords = () => {
  const queryClient = useQueryClient();
  const { alert } = useDialog();

  const { mutate, isPending } = useMutation({
    mutationFn: addRecord,
    mutationKey: [],
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: useFetchLatestRecords.queryKey,
      });
    },
    onError: () => {
      alert('오류가 발생했어요.');
    },
  });

  return { mutate, isPending };
};

export default useAddLatestRecords;

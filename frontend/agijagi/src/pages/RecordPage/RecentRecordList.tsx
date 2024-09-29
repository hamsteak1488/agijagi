import RecordList from '../../components/Record/RecordList';
import useDeleteRecord from '../../hooks/api/useDeleteRecord';

import useFetchRecords from '../../hooks/api/useFetchRecords';
import useDialog from '../../hooks/useDialog';
import useRecord from '../../hooks/useRecord';

const RecentRecordList = () => {
  const { data } = useFetchRecords(1, '2024-09-01', '2024-09-30');
  const { mutate } = useDeleteRecord();

  const { confirm } = useDialog();

  const { findMenuByType } = useRecord();

  const handleListClick = async (id: number) => {
    if (await confirm('선택한 기록을 삭제할까요?')) {
      mutate(id);
      return;
    }
  };

  return (
    <RecordList.Group title="6월 29일 (수요일)">
      {data.map((item) => {
        const menu = findMenuByType(item.type);
        return (
          <RecordList.Item
            key={item.id}
            icon={menu!.icon}
            color={menu!.color}
            title={menu!.type}
            description=""
            date={item.startDateTime}
            onClick={() => handleListClick(item.id)}
          />
        );
      })}
    </RecordList.Group>
  );
};
export default RecentRecordList;

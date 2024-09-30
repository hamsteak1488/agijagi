import dayjs from 'dayjs';
import RecordList from '../../components/Record/RecordList';
import useDeleteRecord from '../../hooks/api/useDeleteRecord';

import useFetchRecords from '../../hooks/api/useFetchRecords';
import useDialog from '../../hooks/useDialog';
import useRecord from '../../hooks/useRecord';

const RecentRecordList = () => {
  const { data } = useFetchRecords(
    1,
    dayjs().subtract(1, 'month').format('YYYY-MM-DD'),
    dayjs().format('YYYY-MM-DD')
  );
  const { mutate } = useDeleteRecord();

  const { confirm } = useDialog();

  const { findMenuByType, groupRecord } = useRecord();

  const handleListClick = async (id: number) => {
    if (await confirm('선택한 기록을 삭제할까요?')) {
      mutate(id);
      return;
    }
  };

  return (
    <>
      {groupRecord([...data].reverse()).map((group) => (
        <RecordList.Group
          key={Object.keys(group)[0]}
          title={Object.keys(group)[0]}
        >
          {Object.values(group)[0].map((item) => {
            const menu = findMenuByType(item.type);
            return (
              <RecordList.Item
                key={item.id}
                icon={menu!.icon}
                color={menu!.color}
                title={menu!.type}
                description=""
                date={`${dayjs(item.startDateTime).format('HH:mm')}${
                  item.endDateTime
                    ? ` ~ ${dayjs(item.endDateTime).format('HH:mm')}`
                    : ''
                }`}
                onClick={() => handleListClick(item.id)}
              />
            );
          })}
        </RecordList.Group>
      ))}
    </>
  );
};
export default RecentRecordList;

import useRecord from '../../hooks/useRecord';

import RecordButton from '../../components/Record/RecordButton';
import useFetchLatestRecords from '../../hooks/apis/useFetchLatestRecords';

const RecordMenu = () => {
  const { data } = useFetchLatestRecords();
  const record = useRecord();

  console.log(data);

  return (
    <>
      {record.getMenu(data).map((menu) => (
        <RecordButton key={menu.type} {...menu} />
      ))}
    </>
  );
};

export default RecordMenu;

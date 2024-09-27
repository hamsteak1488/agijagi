import RecordButton from '../../components/Record/RecordButton';
import RecordList from '../../components/Record/RecordList';
import AppBar from '../../components/common/AppBar';

import useRecord from '../../hooks/useRecord';
import useDialog from '../../hooks/useDialog';

import * as s from './style';

const RecordPage = () => {
  const { confirm } = useDialog();

  const Record = useRecord();

  const 대변 = Record.findMenuByName('대변');
  const 소변 = Record.findMenuByName('소변');
  const 약 = Record.findMenuByName('약');

  const handleListClick = async () => {
    if (!confirm('선택한 기록을 삭제할까요?')) {
      return;
    }
  };

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>기록</AppBar.Title>
      </AppBar>
      <s.Main>
        <s.RecordMenu>
          {Record.getMenu().map((menu) => (
            <RecordButton key={menu.type} {...menu} />
          ))}
        </s.RecordMenu>
        <s.RecordList>
          <RecordList>
            <RecordList.Group title="6월 29일 (수요일)">
              <RecordList.Item
                icon={대변!.icon}
                color={대변!.color}
                title="대변"
                description="많이"
                date="14:00"
                onClick={handleListClick}
              />
              <RecordList.Item
                icon={약!.icon}
                color={약!.color}
                title="약"
                description="감기약"
                date="14:00"
              />
              <RecordList.Item
                icon={소변!.icon}
                color={소변!.color}
                title="소변"
                description="많이"
                date="14:00"
              />
            </RecordList.Group>
          </RecordList>
        </s.RecordList>
      </s.Main>
    </s.Container>
  );
};

export default RecordPage;

import ActivityRecordButton from '../../components/ActivityRecord/ActivityRecordButton';

import ActivityRecordList from '../../components/ActivityRecord/ActivityRecordList';

import AppBar from '../../components/common/AppBar';

import useActivityRecord from '../../hooks/useActivityRecord';

import * as s from './style';

const ActivityRecordPage = () => {
  const activityRecord = useActivityRecord();
  const 대변 = activityRecord.findMenuByName('대변');
  const 소변 = activityRecord.findMenuByName('소변');
  const 약 = activityRecord.findMenuByName('약');

  return (
    <s.Container>
      <AppBar>
        <AppBar.Title>기록</AppBar.Title>
      </AppBar>
      <s.Main>
        <s.ActivityRecordMenu>
          {activityRecord.getMenu().map((menu) => (
            <ActivityRecordButton key={menu.name} {...menu} />
          ))}
        </s.ActivityRecordMenu>
        <s.ActivityRecordList>
          <ActivityRecordList>
            <ActivityRecordList.Group title="6월 29일 (수요일)">
              <ActivityRecordList.Item
                icon={대변!.icon}
                color={대변!.color}
                title="대변"
                description="많이"
                date="14:00"
              />
              <ActivityRecordList.Item
                icon={약!.icon}
                color={약!.color}
                title="약"
                description="감기약"
                date="14:00"
              />
              <ActivityRecordList.Item
                icon={소변!.icon}
                color={소변!.color}
                title="소변"
                description="많이"
                date="14:00"
              />
            </ActivityRecordList.Group>
          </ActivityRecordList>
        </s.ActivityRecordList>
      </s.Main>
    </s.Container>
  );
};

export default ActivityRecordPage;

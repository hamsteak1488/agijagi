import ActivityRecordButton from '../../components/ActivityRecord/ActivityRecordButton';

import ActivityRecordList from '../../components/ActivityRecord/ActivityRecordList';

import AppBar from '../../components/common/AppBar';

import useActivityRecord from '../../hooks/useActivityRecord';

import * as s from '../ActivityRecord/style';

const ActivityRecord = () => {
  const activityRecord = useActivityRecord();

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
                icon={<></>}
                color="#56a4ff"
                title="기저귀"
                description="대변"
                date="14:00"
              />
            </ActivityRecordList.Group>
          </ActivityRecordList>
        </s.ActivityRecordList>
      </s.Main>
    </s.Container>
  );
};

export default ActivityRecord;

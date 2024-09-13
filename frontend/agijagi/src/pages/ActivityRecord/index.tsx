import ActivityRecordButton from '../../components/ActivityRecord/ActivityRecordButton';

import AppBar from '../../components/common/AppBar';

import useActivityRecord from '../../hooks/useActivityRecord';

import * as s from '../ActivityRecord/style';

const ActivityRecord = () => {
  const activityRecord = useActivityRecord();

  return (
    <>
      <AppBar>
        <AppBar.Title>기록</AppBar.Title>
      </AppBar>
      <main>
        <s.ActivityRecordMenu>
          {activityRecord.getMenu().map((menu) => (
            <ActivityRecordButton key={menu.name} {...menu} />
          ))}
        </s.ActivityRecordMenu>
      </main>
    </>
  );
};

export default ActivityRecord;

import Schedule from '..';

import useGetSchedules from '../../../hooks/api/useGetSchedules';
import useModal from '../../../hooks/useModal';

import EditSchedulePage from '../../../pages/SchedulePage/EditSchedulePage';

import useChildStore from '../../../stores/useChlidStore';

interface ScheduleListProps {
  start: string;
  end: string;
}

const List = ({ start, end }: ScheduleListProps) => {
  const { childId } = useChildStore();

  const data = useGetSchedules(childId, start, end);

  const modal = useModal();

  const handleScheduleClick = () => {
    modal.push({ children: <EditSchedulePage /> });
  };

  return (
    <>
      {data.map((item) => (
        <Schedule.Card
          key={item.id}
          title={item.title}
          description={item.description}
          start={item.startDateTime}
          end={item.endDateTime}
          onClick={handleScheduleClick}
        />
      ))}
    </>
  );
};

export default List;

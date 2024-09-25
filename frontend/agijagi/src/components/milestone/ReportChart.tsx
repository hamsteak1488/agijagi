import {
  Line,
  LineChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Label,
  Legend,
  ResponsiveContainer,
} from 'recharts';

const data = [
  {
    "name": 0,
    "avgWeight": 3.2,
    "weight": 3.2,
  },
  {
    "name": 1,
    "avgWeight": 4.5,
    "weight": 4.2,
  },
  {
    "name": 2,
    "avgWeight": 5.1,
    "weight": 4.9,
  },
  {
    "name": 3,
    "avgWeight": 5.8,
    "weight": 5.5,
  },
  {
    "name": 4,
    "avgWeight": 6.4,
    "weight": 6.2,
  },
  {
    "name": 5,
    "avgWeight": 7.0,
    "weight": 6.8,
  },
  // {
  //   "name": 6,
  //   "avgWeight": 7.6,
  //   "weight": 7.4,
  // },
  // {
  //   "name": 7,
  //   "avgWeight": 8.2,
  //   "weight": 8.0,
  // },
  // {
  //   "name": 8,
  //   "avgWeight": 8.8,
  //   "weight": 8.6,
  // },
  // {
  //   "name": 9,
  //   "avgWeight": 9.3,
  //   "weight": 9.0,
  // },
  // {
  //   "name": 10,
  //   "avgWeight": 9.8,
  //   "weight": 9.6,
  // },
  // {
  //   "name": 11,
  //   "avgWeight": 10.2,
  //   "weight": 10.0,
  // },
  // {
  //   "name": 12,
  //   "avgWeight": 10.7,
  //   "weight": 10.5,
  // },
];


const WeightChart = () => {
  return (
    <ResponsiveContainer width="100%" height="100%">
      <LineChart width={350} height={280} data={data} margin={{ top: 20, right: 20, left: 0, bottom: 5 }}>
        <CartesianGrid stroke="#ccc" strokeDasharray="3 3" />
        <YAxis dataKey="weight" label={{value: "kg", position: 'top'}}/>
        <XAxis dataKey="name" type="category" domain={[0, 'maxData']}>
        <Label value="개월" offset={-4} position="insideBottom" />
        </XAxis>
        <Tooltip />
        <Line type="linear" dataKey="weight" stroke="#ff6f00" />
        <Line type="linear" dataKey="avgWeight" stroke="#c0ca33" />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default WeightChart;

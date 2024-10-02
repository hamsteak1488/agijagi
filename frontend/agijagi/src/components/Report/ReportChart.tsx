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
    name: 0,
    avgWeight: 3.2,
    weight: 3.02,
  },
  {
    name: 1,
    avgWeight: 3.7,
    weight: 3.9,
  },
  {
    name: 2,
    avgWeight: 4.5,
    weight: 4.7,
  },
  {
    name: 3,
    avgWeight: 5.2,
    weight: 5.6,
  },
  {
    name: 4,
    avgWeight: 6.0,
    weight: 6.5,
  },
  {
    name: 5,
    avgWeight: 6.8,
    weight: 7.2,
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

interface ChartData {
  day: number;
  weight: number;
  standardWeight: number;
}

interface ChartProps {
  data: ChartData[] | undefined;
}

const WeightChart = ({ data }: ChartProps) => {
  console.log(data)
  return (
    <ResponsiveContainer width="100%" height="100%">
      <LineChart
        width={350}
        height={280}
        data={data}
        margin={{ top: 20, right: 20, left: 0, bottom: 12 }}
      >
        <CartesianGrid stroke="#ccc" strokeDasharray="3 3" />
        <YAxis
          dataKey="weight"
          label={{
            value: 'kg',
            angle: -90,
            position: 'insideLeft',
            offset: 10,
          }}
        />
        <XAxis dataKey="day" type="category" domain={[0, 'maxData']}>
          <Label value="일" offset={-10} position="insideBottom" />
        </XAxis>
        <Tooltip />
        <Legend
          verticalAlign="top"
          align="right"
          wrapperStyle={{ marginTop: -15 }}
        />
        <Line
          type="linear"
          dataKey="weight"
          stroke="#ff6f00"
          name="우리 아이 몸무게"
        />
        <Line
          type="linear"
          dataKey="standardWeight"
          stroke="#c0ca33"
          name="또래 아이 몸무게"
        />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default WeightChart;

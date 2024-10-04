import styled from '@emotion/styled';
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
import Typhography from '../common/Typography';

const ChartWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100px;
`;

interface ChartData {
  day: number;
  weight: number;
  standardWeight: number;
}

interface ChartProps {
  data: ChartData[] | undefined;
}

const WeightChart = ({ data }: ChartProps) => {
  return (
    <>
      {data?.length !== 0 ? (
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
      ) : (
        <ChartWrapper>
          <Typhography color="greyScale" shade="800">
            출생 후 등록한 몸무게 데이터가 없습니다.
          </Typhography>
        </ChartWrapper>
      )}
    </>
  );
};

export default WeightChart;

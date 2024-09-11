import type { Meta, StoryObj } from '@storybook/react';

import Button from '../Button';

import AppBar from './';

const meta = {
  title: 'UI/common/AppBar',
  component: AppBar,
  parameters: {
    layout: 'fullscreen',
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '1rem' }}>
        <Story />
      </div>
    ),
  ],
  tags: ['autodocs'],
  argTypes: {},
} satisfies Meta<typeof AppBar>;

export default meta;

type Story = StoryObj<typeof AppBar>;

export const Primary: Story = {
  args: {},
  render: (args) => (
    <div style={{ width: '360px', height: '500px', backgroundColor: '#ccc' }}>
      <AppBar>
        <Button color="danger" size="sm">
          왼쪽
        </Button>
        <AppBar.Title>페이지 이름</AppBar.Title>
        <Button color="success" size="sm">
          오른쪽
        </Button>
      </AppBar>
    </div>
  ),
};

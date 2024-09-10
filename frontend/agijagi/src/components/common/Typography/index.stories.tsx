import type { Meta, StoryObj } from '@storybook/react';

import Index from './index';

const meta = {
  component: Index,
} satisfies Meta<typeof Index>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    children: '아기자기',
    size: 'md',
    color: 'primary',
    weight: 'regular',
  },
};

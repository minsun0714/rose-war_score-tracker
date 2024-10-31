export const accordionItems = [
  {
    value: 'item-1',
    title: 'Play',
    children: [{ value: 'item-1-1', title: '점수계산', path: '/play' }],
  },
  {
    value: 'item-2',
    title: 'Ranking',
    children: [{ value: 'item-2-1', title: '랭킹', path: '/ranking' }],
  },
  {
    value: 'item-3',
    title: 'Talk',
    children: [
      { value: 'item-3-1', title: '전체글', path: '/board/list' },
      { value: 'item-3-2', title: '글쓰기', path: '/board/write' },
    ],
  },
]

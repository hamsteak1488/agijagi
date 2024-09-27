import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BookCarousel from './components/book/BookCarousel';

import HomePage from './pages/HomePage';
import WritingDiary from './pages/WritingDiary';
import ActivityRecordPage from './pages/ActivityRecordPage';
import SchedulePage from './pages/SchedulePage';
import BabyMain from './pages/BabyMain';
import Login from './pages/Login';
import BoardPage from './pages/BoardPage';
import WriteArticlePage from './pages/BoardPage/WritePage';

import GlobalStyle from './styles/GlobalStyle';

import { ModalProvider } from './hooks/useModal';
import MileStone from './pages/MileStoneCheckPage';
import MileStoneReport from './pages/MileStoneReportPage';
import Report from './pages/ReportPage';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <ModalProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/activity-record" element={<ActivityRecordPage />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/book" element={<BookCarousel />} />
            <Route path="/baby/writing" element={<WritingDiary />} />
            <Route path="/activity-record" element={<ActivityRecordPage />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/schedule" element={<SchedulePage />} />
            <Route path="/milestone" element={<MileStone />} />
            <Route path="/milestone-report" element={<MileStoneReport />} />
            <Route path="/report" element={<Report />} />
            <Route path="/board" element={<BoardPage />} />
            <Route path="/board/write" element={<WriteArticlePage />} />
          </Routes>
        </ModalProvider>
      </BrowserRouter>
    </>
  );
}

export default App;

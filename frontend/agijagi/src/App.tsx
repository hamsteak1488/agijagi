import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom';
import WritingDiary from './pages/WritingDiary';
import RecordPage from './pages/RecordPage';
import SchedulePage from './pages/SchedulePage';
import BabyMain from './pages/Baby/BabyMain';
import Login from './pages/Login';
import BoardPage from './pages/BoardPage';
import WriteArticlePage from './pages/BoardPage/WritePage';
import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';
import MileStone from './pages/MileStoneCheckPage';
import MileStoneReport from './pages/MileStoneReportPage';
import Report from './pages/ReportPage';
import Signup from './pages/Signup';
import { Welcome } from './pages/Signup/Welcome';
import { BabyInfoForm } from './pages/Baby/BabyInfoForm/BabyInfoForm';
import { Main } from './pages/Main';
import BabyProfile from './pages/BabyProfile';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BottomNavigation } from './components/common/BottomNavigation/BottomNavigation';
import BookCarousel from './components/book/BookCarousel';
import Layout from './components/common/Layout';
import StateSynchronizer from './components/StateSynchronizer';

function App() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        retry: 1,
      },
    },
  });

  return (
    <>
      <GlobalStyle />
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          <StateSynchronizer>
            <ModalProvider>
              <Router />
            </ModalProvider>
          </StateSynchronizer>
        </BrowserRouter>
      </QueryClientProvider>
    </>
  );
}

const Router = () => {
  const location = useLocation();
  const showBottomNavigation = [
    '/family',
    '/record',
    '/family/profile',
    '/schedule',
    '/milestone',
    '/milestone-report',
    '/report',
    '/book',
  ].includes(location.pathname);

  return (
    <Layout>
      <Layout.Inner>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/record" element={<RecordPage />} />
          <Route path="/book" element={<BookCarousel />} />
          <Route path="/family" element={<BabyMain />} />
          <Route path="/family/profile" element={<BabyProfile />} />
          <Route path="/family/writing" element={<WritingDiary />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/welcome" element={<Welcome />} />
          <Route path="/schedule" element={<SchedulePage />} />
          <Route path="/milestone" element={<MileStone />} />
          <Route path="/milestone-report" element={<MileStoneReport />} />
          <Route path="/report" element={<Report />} />
          <Route path="/babyform" element={<BabyInfoForm />} />
          <Route path="/board" element={<BoardPage />} />
          <Route path="/main" element={<Main />} />
          <Route path="/board/write" element={<WriteArticlePage />} />
        </Routes>
      </Layout.Inner>
      {showBottomNavigation && <BottomNavigation />}
    </Layout>
  );
};

export default App;

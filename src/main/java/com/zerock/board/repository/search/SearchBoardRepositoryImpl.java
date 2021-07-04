package com.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.QBoard;
import com.zerock.board.entity.QMember;
import com.zerock.board.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

// 1. 쿼리 메소드나 @Query로 처리할 수 없는 쿼리는 별도의 인터페이스로 설계 (Mybatis를 사용해도 된다)
// 2. Querydsl을 사용하기 위해서는 QuerydslRepositorySupport 클래스를 부모클래스로 사용.
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    // QuerydslRepositorySupport의 생성자를 명시적으로 호출하기
    // 이 때 도메인클래스를 넣는다.
    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        // 1. 조인절 작성
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        // 2. SELECT 절 작성
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        // 3. WHERE 절 작성
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression booleanExpression = board.bno.gt(0L);

        booleanBuilder.and(booleanExpression);

        if(type != null){
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String t : typeArr){
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        // 4. Order by절 작성
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        // 5. Group by 절 작성
        tuple.groupBy(board);

        // 6. page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();
        // 서버단에서 tuple.fetch()로 가져온 데이터들을 원하는 형태로 가공하여 반환하고자 할 때 PageImpl 사용
        // 여기서는 각 조회한 tuple의 행들을 배열로 만들어서 리스트로 변환 후 반환.
        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}

package com.team14.cherrybnb.room.application;

import com.team14.cherrybnb.auth.domain.Member;
import com.team14.cherrybnb.common.exception.BusinessException;
import com.team14.cherrybnb.common.exception.ErrorCode;
import com.team14.cherrybnb.room.domain.Room;
import com.team14.cherrybnb.room.domain.RoomRepository;
import com.team14.cherrybnb.room.domain.Wish;
import com.team14.cherrybnb.room.domain.WishRepository;
import com.team14.cherrybnb.room.dto.wish.WishCardResponse;
import com.team14.cherrybnb.room.dto.wish.WishRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team14.cherrybnb.common.exception.ErrorCode.*;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final RoomRepository roomRepository;

    public WishService(WishRepository wishRepository, RoomRepository roomRepository) {
        this.wishRepository = wishRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional(readOnly = true)
    public Page<WishCardResponse> getWishes(Pageable pageable, Member member) {
        Page<Wish> wishes = wishRepository.findAllByMemberId(pageable, member);

        return wishes.map(WishCardResponse::new);
    }

    @Transactional
    public void addWish(Member member, WishRequest wishRequest) {
        Room room = roomRepository.findById(wishRequest.getRoomId())
                .orElseThrow(() -> new BusinessException(NO_SUCH_ROOM));

        wishRepository.save(Wish.of(member, room));
    }

    @Transactional
    public void removeWish(Long wishId, Member loginMember) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> new BusinessException(NO_SUCH_WISH));

        if (!loginMember.isSame(wish.getMember())) {
            throw new BusinessException(NO_SUCH_MEMBER);
        }
        wishRepository.delete(wish);
    }
}

package com.team14.cherrybnb.room.application;

import com.team14.cherrybnb.auth.domain.Member;
import com.team14.cherrybnb.room.domain.Room;
import com.team14.cherrybnb.room.domain.RoomRepository;
import com.team14.cherrybnb.room.domain.Wish;
import com.team14.cherrybnb.room.domain.WishRepository;
import com.team14.cherrybnb.room.dto.wish.WishCardResponse;
import com.team14.cherrybnb.room.dto.wish.WishRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final RoomRepository roomRepository;

    public WishService(WishRepository wishRepository, RoomRepository roomRepository) {
        this.wishRepository = wishRepository;
        this.roomRepository = roomRepository;
    }

    public Page<WishCardResponse> getWishes(Pageable pageable, Member member) {
        Page<Wish> wishes = wishRepository.findAllByMemberId(pageable, member);
        List<WishCardResponse> wishCardResponses = wishes.getContent()
                .stream()
                .map(WishCardResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(wishCardResponses, pageable, wishes.getTotalElements());
    }
    
    public void addWish(Member member, WishRequest wishRequest) {
        Room room = roomRepository.findById(wishRequest.getRoomId())
                .orElseThrow(RuntimeException::new);

        wishRepository.save(Wish.of(member, room));
    }

    public void removeWish(Long wishId, Member loginMember) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(RuntimeException::new);
        if (!loginMember.isSame(wish.getMember())) {
            throw new RuntimeException();
        }
        wishRepository.delete(wish);
    }
}
